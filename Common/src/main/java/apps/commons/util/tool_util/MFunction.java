package apps.commons.util.tool_util;

import java.io.Serializable;
import java.util.function.Function;

/**
 * 支持序列化的 Function
 * @author miemie
 * @since 2018-05-12
 */
@FunctionalInterface
public interface MFunction<T, R> extends Function<T, R>, Serializable {
}
