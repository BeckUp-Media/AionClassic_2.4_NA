/*
 *  Aion Classic Emu based on Aion Encom Source Files
 *
 *  ENCOM Team based on Aion-Lighting Open Source
 *  All Copyrights : "Data/Copyrights/AEmu-Copyrights.text
 *
 *  iMPERIVM.FUN - AION DEVELOPMENT FORUM
 *  Forum: <http://https://imperivm.fun/>
 *
 */
package quest.eltnen;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.questEngine.model.QuestDialog;
import com.aionemu.gameserver.services.QuestService;

/****/
/** Author Rinzler (Encom)
/****/

public class _1037Secrets_Of_The_Temple extends QuestHandler
{
	private final static int questId = 1037;
	private final static int[] npcs = {203965, 203967, 700150, 700151, 700152, 700153, 700154};
	
	public _1037Secrets_Of_The_Temple() {
		super(questId);
	}
	
	@Override
	public void register() {
		qe.registerOnLevelUp(questId);
		qe.registerOnEnterZoneMissionEnd(questId);
		for (int npc: npcs) {
            qe.registerQuestNpc(npc).addOnTalkEvent(questId);
        } 
	}
	
	@Override
	public boolean onZoneMissionEndEvent(QuestEnv env) {
		return defaultOnZoneMissionEndEvent(env);
	}
	
	@Override
	public boolean onLvlUpEvent(QuestEnv env) {
		return defaultOnLvlUpEvent(env);
	}
	
	@Override
	public boolean onDialogEvent(final QuestEnv env) {
		final Player player = env.getPlayer();
        final QuestState qs = player.getQuestStateList().getQuestState(questId);
		int targetId = env.getTargetId();
        int var = qs.getQuestVarById(0);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
			if (targetId == 203965) {
				switch (env.getDialog()) {
					case START_DIALOG: {
						if (var == 0) {
							return sendQuestDialog(env, 1011);
						}
					} case STEP_TO_1: {
						changeQuestStep(env, 0, 1, false);
						return closeDialogWindow(env);
					}
				}
			} if (targetId == 203967) {
				switch (env.getDialog()) {
					case START_DIALOG: {
						if (var == 1) {
							return sendQuestDialog(env, 1352);
						} else if (var == 2 && QuestService.collectItemCheck(env, true)) {
							return sendQuestDialog(env, 1695);
						} else {
							return sendQuestDialog(env, 1779);
						}
					} case STEP_TO_2: {
						changeQuestStep(env, 1, 2, false);
						return closeDialogWindow(env);
					} case STEP_TO_3: {
						giveQuestItem(env, 182201027, 1);
						changeQuestStep(env, 2, 3, false);
						return closeDialogWindow(env);
					}
				}
			} if (targetId == 700151) {
				switch (env.getDialog()) {
                    case USE_OBJECT: {
						if (var == 3) {
							return useQuestObject(env, 3, 4, false, false);
						}
					}
                }
			} if (targetId == 700154) {
				switch (env.getDialog()) {
                    case USE_OBJECT: {
						if (var == 4) {
							return useQuestObject(env, 4, 5, false, false);
						}
					}
                }
			} if (targetId == 700150) {
				switch (env.getDialog()) {
					case USE_OBJECT: {
						if (var == 5) {
							return useQuestObject(env, 5, 6, false, false);
						}
					}
				}
			} if (targetId == 700153) {
				switch (env.getDialog()) {
					case USE_OBJECT: {
						if (var == 6) {
							return useQuestObject(env, 6, 7, false, false);
						}
					}
				}
			} if (targetId == 700152) {
				switch (env.getDialog()) {
					case USE_OBJECT: {
						if (var == 7) {
							return useQuestObject(env, 7, 7, true, 0, 0, 0, 182201027, 1, 33);
						}
					}
				}
			}
		} else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 203965) {
                if (env.getDialog() == QuestDialog.USE_OBJECT) {
                    return sendQuestDialog(env, 2034);
				} else if (env.getDialog() == QuestDialog.SELECT_REWARD) {
					return sendQuestDialog(env, 5);
				} else {
					return sendQuestEndDialog(env);
				}
			}
		}
		return false;
	}
}