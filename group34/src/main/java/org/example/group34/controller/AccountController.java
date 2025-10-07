package org.example.group34.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.example.group34.model.User;
import org.example.group34.service.CustomUserDetails;
import org.example.group34.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile; // IMPORTANT for file upload
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;
    @Autowired
    private CustomUserDetails customUserDetails;

    @GetMapping
    public String accountPage(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "account";
    }

    @PostMapping("/username")
    public String changeUsername(@RequestParam("newUsername") String newUsername,
                                 Principal principal,
                                 RedirectAttributes redirectAttributes,
                                 HttpServletRequest request) {
        // Find the currently logged-in user
        User user = userService.findByUsername(principal.getName());

        // Check if the new username is already taken
        if (userService.findByUsername(newUsername) != null) {
            redirectAttributes.addFlashAttribute("duplicate", "Username already exists.");
            return "redirect:/account";
        }

        // Update the username in the database
        userService.updateUsername(user, newUsername);

        // Re-authenticate with the new username using CustomUserDetails
        UserDetails updatedUserDetails = customUserDetails.loadUserByUsername(newUsername);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                updatedUserDetails, updatedUserDetails.getPassword(), updatedUserDetails.getAuthorities()
        );

        // Set the updated authentication context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Update the session to reflect the new authentication
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());

        redirectAttributes.addFlashAttribute("success", "Username updated successfully.");
        return "redirect:/account";  // user is authenticated with new username
    }



    @PostMapping("/password")
    public String changePassword(@RequestParam("oldPassword") String oldPassword, // Capture the old password from the form input
                                 @RequestParam("newPassword") String newPassword, // Capture the new password from the form input
                                 Principal principal, // Get the currently authenticated user's principal
                                 RedirectAttributes redirectAttributes) {

        // Find the user by their username
        User user = userService.findByUsername(principal.getName());

        // Check if the provided old password matches the current password stored in the database
        if (!userService.checkPassword(user, oldPassword)) {
            redirectAttributes.addFlashAttribute("error", "Incorrect current password.");
            return "redirect:/account"; // Redirect to account page
        }

        // Regular expression pattern for validating the new password
        String passwordPattern = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*.])(?=\\S+$).*$";

        // Check if the new password matches the pattern
        if (!newPassword.matches(passwordPattern)) {
            redirectAttributes.addFlashAttribute("error",
                    "Password must be at least 8 characters, contain 1 uppercase letter, and 1 special character (@#$%^&+=!).");
            return "redirect:/account"; // Redirect to account page
        }

        // If the new password is valid, update the password in the database
        userService.updatePassword(user, newPassword);

        // Add a success message and redirect back to the account page
        redirectAttributes.addFlashAttribute("success", "Password changed successfully.");
        return "redirect:/account"; // Redirect to account page with success message
    }


    @PostMapping("/profile-picture")
    public String uploadProfilePicture(@RequestParam("imageFile") MultipartFile imageFile,
                                       Principal principal,
                                       RedirectAttributes redirectAttributes) {
        if (imageFile.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Please select an image to upload.");
            return "redirect:/account";
        }

        try {
            // Find the currently logged-in user
            User user = userService.findByUsername(principal.getName());
            // Store the file bytes in the user's profilePicture field
            user.setProfilePicture(imageFile.getBytes());
            userService.saveUser(user);

            redirectAttributes.addFlashAttribute("success", "Profile picture uploaded successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error uploading image.");
        }

        return "redirect:/account";
    }

}
