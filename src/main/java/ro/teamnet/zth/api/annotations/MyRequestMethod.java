package ro.teamnet.zth.api.annotations;

import java.lang.annotation.*;

/**
 * Created by Adi on 06.05.2015.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyRequestMethod {
    String methodType() default "GET";
    String urlPath();
}
