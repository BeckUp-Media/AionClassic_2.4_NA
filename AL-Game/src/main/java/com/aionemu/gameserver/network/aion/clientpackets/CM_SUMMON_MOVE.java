package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.controllers.movement.MovementMask;
import com.aionemu.gameserver.controllers.movement.SummonMoveController;
import com.aionemu.gameserver.model.gameobjects.Summon;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.serverpackets.S_MOVE_NEW;
import com.aionemu.gameserver.skillengine.effect.AbnormalState;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;

public class CM_SUMMON_MOVE extends AionClientPacket
{
	private byte type;
	private byte heading;
	private float x = 0f, y = 0f, z = 0f, x2 = 0f, y2 = 0f, z2 = 0f, vehicleX = 0f, vehicleY = 0f, vehicleZ = 0f, vectorX = 0f, vectorY = 0f, vectorZ = 0f;
	private byte glideFlag;
	private int unk1, unk2;
	
	public CM_SUMMON_MOVE(int opcode, State state, State... restStates) {
		super(opcode, state, restStates);
	}
	
	@Override
	protected void readImpl() {
		Player player = getConnection().getActivePlayer();
		if (player == null || !player.isSpawned())
			return;
		readD();
		x = readF();
		y = readF();
		z = readF();
		heading = (byte) readC();
		type = (byte) readC();
		if ((type & MovementMask.STARTMOVE) == MovementMask.STARTMOVE) {
			if ((type & MovementMask.MOUSE) == 0) {
				x2 = vectorX + x;
				y2 = vectorY + y;
				z2 = vectorZ + z;
			} else {
				x2 = readF();
				y2 = readF();
				z2 = readF();
			}
		} if ((type & MovementMask.GLIDE) == MovementMask.GLIDE) {
			glideFlag = (byte) readC();
		} if ((type & MovementMask.VEHICLE) == MovementMask.VEHICLE) {
			unk1 = readD();
			unk2 = readD();
			vehicleX = readF();
			vehicleY = readF();
			vehicleZ = readF();
		}
	}
	
	@Override
	protected void runImpl() {
		Player player = getConnection().getActivePlayer();
		Summon summon = player.getSummon();
		if (summon == null)
			return;
		if (summon.getEffectController().isAbnormalState(AbnormalState.CANT_MOVE_STATE) || summon.getEffectController().isUnderFear())
			return;
		SummonMoveController m = summon.getMoveController();
		m.movementMask = type;
		if ((type & MovementMask.GLIDE) == MovementMask.GLIDE) {
			m.glideFlag = glideFlag;
		} if (type == 0) {
			summon.getController().onStopMove();
		} else if ((type & MovementMask.STARTMOVE) == MovementMask.STARTMOVE) {
			if ((type & MovementMask.MOUSE) == 0) {
				m.vectorX = vectorX;
				m.vectorY = vectorY;
				m.vectorZ = vectorZ;
			}
			summon.getMoveController().setNewDirection(x2, y2, z2, heading);
			summon.getController().onStartMove();
		} else {
			summon.getController().onMove();
		}
		if ((type & MovementMask.VEHICLE) == MovementMask.VEHICLE) {
			m.unk1 = unk1;
			m.unk2 = unk2;
			m.vehicleX = vehicleX;
			m.vehicleY = vehicleY;
			m.vehicleZ = vehicleZ;
		}
		World.getInstance().updatePosition(summon, x, y, z, heading);
		if ((type & MovementMask.STARTMOVE) == MovementMask.STARTMOVE || type == 0) {
			PacketSendUtility.broadcastPacket(summon, new S_MOVE_NEW(summon));
		}
	}
}