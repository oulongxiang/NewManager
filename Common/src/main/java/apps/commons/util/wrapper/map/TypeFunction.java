package apps.commons.util.wrapper.map;

import java.io.Serializable;
import java.util.function.Function;

/**
 * @author zengyu
 */
@FunctionalInterface
public interface TypeFunction<T, R> extends Serializable, Function<T, R> {

}
