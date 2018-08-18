package us.deloitteinnovation.aca.security;

/**
 * Interface for an profile-specific authentication holder
 *
 * @since 2.0.0
 * @author yaojia
 */

public interface IAuthenticationHolder {
    UserSession getUserSession();
}
