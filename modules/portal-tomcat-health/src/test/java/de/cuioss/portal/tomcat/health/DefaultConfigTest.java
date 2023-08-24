/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.portal.tomcat.health;

import static de.cuioss.portal.configuration.HealthCheckConfigKeys.PORTAL_HEALTHCHECK_ROLES_REQUIRED;
import static de.cuioss.tools.collect.CollectionLiterals.immutableList;

import java.util.List;

import de.cuioss.portal.configuration.FileConfigurationSource;
import de.cuioss.portal.configuration.HealthCheckConfigKeys;
import de.cuioss.portal.core.test.tests.configuration.AbstractConfigurationKeyVerifierTest;
import de.cuioss.portal.core.test.tests.configuration.PropertiesDefaultConfigSource;
import lombok.Getter;

class DefaultConfigTest extends AbstractConfigurationKeyVerifierTest {

    @Getter
    private final FileConfigurationSource underTest = new PropertiesDefaultConfigSource();

    @Override
    public Class<?> getKeyHolder() {
        return HealthCheckConfigKeys.class;
    }

    @Override
    public List<String> getKeysIgnoreList() {
        return immutableList(PORTAL_HEALTHCHECK_ROLES_REQUIRED);
    }
}
