package com.sample.auctions.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.auctions.dto.user.ChangePasswordDto;
import com.sample.auctions.model.user.User;
import com.sample.auctions.security.CurrentUser;
import com.sample.auctions.security.UserDetailsImpl;
import com.sample.auctions.service.interfaces.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

    final UserService userService;

    @GetMapping()
    @Operation(summary = "Get all users (only for seller)")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/change-password")
    @Operation(summary = "Change password")
    public ResponseEntity<?> changePassword(@CurrentUser UserDetailsImpl userDetails,
                                            ChangePasswordDto changePasswordDto) {
        userService.changePassword(userDetails.getId(), changePasswordDto.getOldPassword(),
                changePasswordDto.getNewPassword());
        return ResponseEntity.ok("Password changed successfully");

    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete user by id")
    public void deleteUserById(@PathVariable Long userId) {
        userService.deleteUserById(userId);
    }
}
