package coo.core.security.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 高级日志注解。
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Log {
	/** 指定日志记录的属性名称 */
	String text();

	/** 当属性是关联对象时，指定记录关联对象哪个属性 */
	String property() default "";

	/** 当属性是日期类型时，指定记录的日期格式 */
	String format() default "";
}