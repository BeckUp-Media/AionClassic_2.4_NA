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
package com.aionemu.gameserver.restrictions;

import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.model.Skill;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class ShutdownRestrictions extends AbstractRestrictions
{
	@Override
	public boolean isRestricted(Player player, Class<? extends Restrictions> callingRestriction) {
		if (isInShutdownProgress(player)) {
			PacketSendUtility.sendMessage(player, "You are in shutdown progress!");
			return true;
		}
		return false;
	}
	
	@Override
	public boolean canAttack(Player player, VisibleObject target) {
		if (isInShutdownProgress(player)) {
			PacketSendUtility.sendMessage(player, "You cannot attack in Shutdown progress!");
			return false;
		}
		return true;
	}
	
	@Override
    public boolean canAffectBySkill(Player player, VisibleObject target, Skill skill) {
		return true;
	}
	
	@Override
	public boolean canUseSkill(Player player, Skill skill) {
		if (isInShutdownProgress(player)) {
			PacketSendUtility.sendMessage(player, "You cannot use skills in Shutdown progress!");
			return false;
		}
		return true;
	}
	
	@Override
	public boolean canChat(Player player) {
		if (isInShutdownProgress(player)) {
			PacketSendUtility.sendMessage(player, "You cannot chat in Shutdown progress!");
			return false;
		}
		return true;
	}
	
	@Override
	public boolean canInviteToGroup(Player player, Player target) {
		if (isInShutdownProgress(player)) {
			PacketSendUtility.sendMessage(player, "You cannot invite members to group in Shutdown progress!");
			return false;
		}
		return true;
	}
	
	@Override
	public boolean canLeaveGroup(Player player) {
		if (isInShutdownProgress(player)) {
			PacketSendUtility.sendMessage(player, "You cannot leave group in Shutdown progress!");
			return false;
		}
		return true;
	}
	
	@Override
	public boolean canInviteToAlliance(Player player, Player target) {
		if (isInShutdownProgress(player)) {
			PacketSendUtility.sendMessage(player, "You cannot invite members to alliance in Shutdown progress!");
			return false;
		}
		return true;
	}
	
	@Override
	public boolean canChangeEquip(Player player) {
		if (isInShutdownProgress(player)) {
			PacketSendUtility.sendMessage(player, "You cannot equip / unequip item in Shutdown progress!");
			return false;
		}
		return true;
	}
	
	private boolean isInShutdownProgress(Player player) {
		return player.getController().isInShutdownProgress();
	}
}