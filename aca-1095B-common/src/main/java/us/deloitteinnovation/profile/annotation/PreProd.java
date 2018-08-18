package us.deloitteinnovation.profile.annotation;

import org.springframework.context.annotation.Profile;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * PreProd Profile Annotation
 *
 * @author ltornquist
 * @since 12/01/2015
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Profile("preprod")
public @interface PreProd {
}
