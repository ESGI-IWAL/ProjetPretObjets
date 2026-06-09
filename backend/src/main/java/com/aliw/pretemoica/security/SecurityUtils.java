package com.aliw.pretemoica.security;

import com.aliw.pretemoica.exception.UnauthorizedException;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    /**     * Récupère l'ID de l'utilisateur actuellement authentifié     * @return L'ID de l'utilisateur     * @throws IllegalStateException si l'utilisateur n'est pas authentifié     */
    public static Long getCurrentUserId() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new IllegalStateException("Utilisateur non authentifié");
        }

        // L'ID est stocké dans les détails par JwtAuthenticationFilter
        if (auth.getDetails() instanceof Long) {
            return (Long) auth.getDetails();
        }

        throw new IllegalStateException("ID utilisateur non disponible");
    }

    /**     * Vérifie si l'utilisateur est authentifié     */
    public static boolean isAuthenticated() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.isAuthenticated();
    }
}