/*
 * This file is part of aion-unique <aionunique.smfnew.com>.
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
package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.configs.main.CustomConfig;
import com.aionemu.gameserver.model.gameobjects.AionObject;
import com.aionemu.gameserver.model.gameobjects.player.DeniedStatus;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.serverpackets.S_MESSAGE_CODE;
import com.aionemu.gameserver.services.DuelService;

/**
 * @author xavier
 */
public class CM_DUEL_REQUEST extends AionClientPacket {

	/**
	 * Target object id that client wants to start duel with
	 */
	private int objectId;

	/**
	 * Constructs new instance of <tt>CM_DUEL_REQUEST</tt> packet
	 * 
	 * @param opcode
	 */
	public CM_DUEL_REQUEST(int opcode, State state, State... restStates) {
		super(opcode, state, restStates);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void readImpl() {
		objectId = readD();
	}

	@Override
	protected void runImpl() {
		Player activePlayer = getConnection().getActivePlayer();
		AionObject target = activePlayer.getKnownList().getObject(objectId);

		if (!CustomConfig.INSTANCE_DUEL_ENABLE && activePlayer.isInInstance())
			return;
		
		if (target == null)
			return;

		if (target instanceof Player && !((Player) target).equals(activePlayer)) {
			DuelService duelService = DuelService.getInstance();

			Player targetPlayer = (Player) target;

			if (duelService.isDueling(activePlayer.getObjectId())) {
				sendPacket(S_MESSAGE_CODE.STR_DUEL_YOU_ARE_IN_DUEL_ALREADY);
				return;
			}
			if (duelService.isDueling(targetPlayer.getObjectId())) {
				sendPacket(S_MESSAGE_CODE.STR_DUEL_PARTNER_IN_DUEL_ALREADY(target.getName()));
				return;
			}
			if (targetPlayer.getPlayerSettings().isInDeniedStatus(DeniedStatus.DUEL)) {
				sendPacket(S_MESSAGE_CODE.STR_MSG_REJECTED_DUEL(targetPlayer.getName()));
				return;
			}
			duelService.onDuelRequest(activePlayer, targetPlayer);
			duelService.confirmDuelWith(activePlayer, targetPlayer);
		}
		else {
			sendPacket(S_MESSAGE_CODE.STR_DUEL_PARTNER_INVALID(target.getName()));
		}
	}
}
