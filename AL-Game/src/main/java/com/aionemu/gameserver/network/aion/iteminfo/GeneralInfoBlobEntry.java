/*
 * This file is part of aion-lightning <js-emu.ru>.
 *
 *  aion-lightning is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-lightning is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-lightning.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.network.aion.iteminfo;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.items.ItemId;
import com.aionemu.gameserver.network.aion.iteminfo.ItemInfoBlob.ItemBlobType;

import java.nio.ByteBuffer;

/**
 * This blob entry is sent with ALL items. (unless partial blob is constructed, ie: sending equip slot only) It is the
 * first and only block for non-equipable items, and the last blob for EquipableItems
 * 
 * @author -Nemesiss-
 * @modified Rolandas
 */
public class GeneralInfoBlobEntry extends ItemBlobEntry {
	GeneralInfoBlobEntry() {
		super(ItemBlobType.GENERAL_INFO);
	}

	@Override
	public void writeThisBlob(ByteBuffer buf) {// TODO what with kinah?
		Item item = ownerItem;
		writeH(buf, item.getItemMask(owner));
		writeQ(buf, item.getItemCount());
		writeS(buf, item.getItemCreator());// Creator name
		writeC(buf, 0);
		writeD(buf, item.getExpireTimeRemaining()); // Disappears time
		writeD(buf, 0);
		writeD(buf, item.getTemporaryExchangeTimeRemaining());
		writeD(buf, 0);
		writeH(buf, item.getItemId() == ItemId.KINAH.value() ? 50 : 0);
	}

	@Override
	public int getSize() {
		return 29 + ownerItem.getItemCreator().length() * 2 + 2;
	}
}
