package com.github.invictum.reportportal;

import com.epam.ta.reportportal.ws.model.log.SaveLogRQ;
import com.github.invictum.reportportal.log.unit.Attachment;
import com.github.invictum.reportportal.log.unit.Error;
import com.github.invictum.reportportal.log.unit.Essentials;
import com.github.invictum.reportportal.log.unit.Selenium;
import com.google.common.base.Preconditions;
import net.thucydides.core.model.TestStep;

import java.util.Collection;
import java.util.function.Function;

/**
 * Describes a presets of predefined log units.
 */
@SuppressWarnings("unchecked")
public enum LogsPreset {

    /**
     * Default preset with pre-defined minimalistic set of log units.
     */
    DEFAULT {
        @Override
        Function<TestStep, Collection<SaveLogRQ>>[] logUnits() {
            return new Function[]{
                    Essentials.finishStep(),
                    Attachment.screenshots(),
                    Error.basic()
            };
        }
    },

    /**
     * Preset configured with all available out of the box log units.
     */
    FULL {
        @Override
        Function<TestStep, Collection<SaveLogRQ>>[] logUnits() {
            return new Function[]{
                    Essentials.startStep(),
                    Attachment.screenshots(),
                    Essentials.finishStep(),
                    Error.basic(),
                    Attachment.htmlSources(),
                    Selenium.allLogs()
            };
        }
    },

    /**
     * Custom preset for manual configuration. By default returns an empty array of log units.
     */
    CUSTOM {

        private Function<TestStep, Collection<SaveLogRQ>>[] units;

        @Override
        Function<TestStep, Collection<SaveLogRQ>>[] logUnits() {
            return units == null ? new Function[0] : units;
        }

        @Override
        @SafeVarargs
        final public LogsPreset register(Function<TestStep, Collection<SaveLogRQ>>... units) {
            Preconditions.checkArgument(units != null, "Units list must not be null");
            this.units = units;
            return this;
        }
    },

    /**
     * Preset designed to use in TREE handler mode.
     */
    TREE_OPTIMIZED {
        @Override
        Function<TestStep, Collection<SaveLogRQ>>[] logUnits() {
            return new Function[]{
                    Attachment.screenshots(),
                    Error.basic()
            };
        }
    };

    /**
     * Returns an array of log units associated with current preset
     */
    abstract Function<TestStep, Collection<SaveLogRQ>>[] logUnits();

    /**
     * Configures a preset to use a list of passed log units. Registration is allowed only for CUSTOM preset
     */
    public LogsPreset register(Function<TestStep, Collection<SaveLogRQ>>... units) {
        throw new UnsupportedOperationException("Only CUSTOM preset allows to register log units");
    }
}
