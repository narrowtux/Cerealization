/*
 * This file is part of Cerealization.
 *
 * Copyright (c) 2013 Spout LLC <http://www.spout.org/>
 * Cerealization is licensed under the Spout License Version 1.
 *
 * Cerealization is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the Spout License Version 1.
 *
 * Cerealization is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the Spout License Version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://spout.in/licensev1> for the full license, including
 * the MIT license.
 */
package org.spout.cereal.config;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A base class for configurations that load their values from a {@link Map}
 */
public abstract class MapBasedConfiguration extends AbstractConfiguration {
	/**
	 * Implementations can use this method to provide the necessary data for calls of load.
	 * @return A map with raw configuration data
	 * @throws ConfigurationException when an error occurs while loading.
	 */
	protected abstract Map<?, ?> loadToMap() throws ConfigurationException;

	/**
	 * Save the  data from this configuration. This method is called from {@link #save()}
	 * @param map Configuration as a set of nested Maps
	 * @throws ConfigurationException When an error occurs while saving the given data.
	 */
	protected abstract void saveFromMap(Map<?, ?> map) throws ConfigurationException;

	@Override
	protected Map<String, ConfigurationNode> loadToNodes() throws ConfigurationException {
		Map<?, ?> items = loadToMap();
		Map<String, ConfigurationNode> children = new LinkedHashMap<String, ConfigurationNode>();
		for (Map.Entry<?, ?> entry : items.entrySet()) {
			children.put(entry.getKey().toString(), createConfigurationNode(new String[] {entry.getKey().toString()}, entry.getValue()));
		}
		return children;
	}

	@Override
	protected void saveFromNodes(Map<String, ConfigurationNode> nodes) throws ConfigurationException {
		Map<String, Object> ret = new LinkedHashMap<String, Object>();
		for (Map.Entry<String, ConfigurationNode> entry : getChildren().entrySet()) {
			ret.put(entry.getKey(), entry.getValue().getValue());
		}
		saveFromMap(ret);
	}
}
