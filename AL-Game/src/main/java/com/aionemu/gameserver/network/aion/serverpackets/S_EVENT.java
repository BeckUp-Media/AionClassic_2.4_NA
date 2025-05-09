/*
 * This file is part of aion-unique <aion-unique.org>.
 *
 *  aion-unique is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-unique is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-unique.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.items.storage.StorageType;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author Sweetkr
 */
public class S_EVENT extends AionServerPacket {

	private int action;
	/**
	 * for action 0 - its storage type<br>
	 * for action 8 - its advanced stigma count
	 */
	private int actionValue;

	private int itemsCount;
	private int npcExpands;
	private int questExpands;

	public static S_EVENT stigmaSlots(int slots)
	{
		return new S_EVENT(6, slots);
	}

	public static S_EVENT cubeSize(StorageType type, Player player)
	{
		int itemsCount = 0;
		int npcExpands = 0;
		int questExpands = 0;
		switch(type) {
			case CUBE:
				itemsCount = player.getInventory().size();
				npcExpands = player.getNpcExpands();
				questExpands = player.getQuestExpands();
				break;
			case REGULAR_WAREHOUSE:
				itemsCount = player.getWarehouse().size();
				npcExpands = player.getWarehouseSize();
				//questExpands = ?? //TODO!
				break;
			case LEGION_WAREHOUSE:
				itemsCount = player.getLegion().getLegionWarehouse().size();
				npcExpands = player.getLegion().getWarehouseLevel();
				break;
		}
		
		return new S_EVENT(0, type.ordinal(), itemsCount, npcExpands, questExpands);
	}

	private S_EVENT(int action, int actionValue, int itemsCount, int npcExpands, int questExpands) {
		this(action, actionValue);
		this.itemsCount = itemsCount;
		this.npcExpands = npcExpands;
		this.questExpands = questExpands;
	}

	private S_EVENT(int action, int actionValue) {
		this.action = action;
		this.actionValue = actionValue;
	}

	@Override
	protected void writeImpl(AionConnection con) {
		writeC(action);
		writeC(actionValue);
		switch (action) {
			case 0:
				writeD(itemsCount);
				writeC(npcExpands); // cube size from npc (so max 5 for now)
				writeC(questExpands); // cube size from quest (so max 2 for now)
				writeC(0); // unk - expands from items?
				break;
			case 6:
				break;
			default:
				break;
		}
	}
}
