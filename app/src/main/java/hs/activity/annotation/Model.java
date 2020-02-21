package hs.activity.annotation;

import androidx.annotation.IntDef;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@IntDef(value = {SHAPE.RECTANGLE, SHAPE.TRIANGLE, SHAPE.SQUARE, SHAPE.CIRCLE})
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.SOURCE)
public @interface Model {

}