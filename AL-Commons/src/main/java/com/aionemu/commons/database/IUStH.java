/*
 * This file is part of aion-emu <aion-emu.com>.
 *
 *  aion-emu is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-emu is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-emu.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.commons.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Insert/Update Statement handler.<br>
 * For usage details check documentation of DB class.
 *
 * @author Disturbing
 */
public interface IUStH
{
	/**
	 * Enables coder to manually modify statement or batch. Must execute batch or statement manually. Automatically
	 * recycles connection.
	 *
	 * @param stmt
	 * @throws SQLException
	 */
	void handleInsertUpdate(PreparedStatement stmt) throws SQLException;
}
