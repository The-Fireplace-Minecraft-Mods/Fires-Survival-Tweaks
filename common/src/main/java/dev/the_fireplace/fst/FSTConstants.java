package dev.the_fireplace.fst;

import com.google.inject.Injector;
import dev.the_fireplace.annotateddi.api.Injectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class FSTConstants
{
    public static final String MODID = "fst";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static Injector getInjector() {
        return Injectors.INSTANCE.getAutoInjector(MODID);
    }
}
