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
package quest.arena;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.handlers.HandlerResult;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.questEngine.model.QuestDialog;

/****/
/** Author Rinzler (Encom)
/****/

public class _18213The_Chilling_Truth extends QuestHandler
{
	private final static int questId = 18213;

	public _18213The_Chilling_Truth() {
		super(questId);
	}
	
	@Override
    public void register() {
		qe.registerQuestItem(182212220, questId);
        qe.registerQuestNpc(205985).addOnQuestStart(questId);
		qe.registerQuestNpc(205985).addOnTalkEvent(questId);
        qe.registerQuestNpc(205316).addOnTalkEvent(questId);
        qe.registerQuestNpc(798604).addOnTalkEvent(questId);
    }
	
    @Override
    public boolean onDialogEvent(final QuestEnv env) {
        final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		int targetId = env.getTargetId();
        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (targetId == 205985) {
                switch (env.getDialog()) {
                    case START_DIALOG: {
						return sendQuestDialog(env, 1011);
					} case ASK_ACCEPTION: {
						return sendQuestDialog(env, 4);
					} case ACCEPT_QUEST: {
						return sendQuestStartDialog(env, 182212220, 1);
					} case REFUSE_QUEST: {
				        return closeDialogWindow(env);
					}
                }
            }
        } else if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
			if (targetId == 205316) {
                switch (env.getDialog()) {
                    case START_DIALOG: {
						if (var == 0) {
							return sendQuestDialog(env, 1352);
						}
					} case STEP_TO_1: {
						changeQuestStep(env, 0, 1, false);
						return closeDialogWindow(env);
					}
                }
            } if (targetId == 798604) {
                switch (env.getDialog()) {
                    case START_DIALOG: {
						if (var == 1) {
							return sendQuestDialog(env, 1693);
						} else if (var == 3) {
							return sendQuestDialog(env, 2375);
						}
					} case STEP_TO_2: {
						changeQuestStep(env, 1, 2, false);
						return closeDialogWindow(env);
					} case SELECT_REWARD: {
						return defaultCloseDialog(env, 3, 3, true, false);
					}
                }
            }
        } else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 798604) {
                if (env.getDialog() == QuestDialog.USE_OBJECT) {
                    return sendQuestDialog(env, 5);
                } else {
                    return sendQuestEndDialog(env);
                }
            }
        }
        return false;
    }
	
	@Override
    public HandlerResult onItemUseEvent(final QuestEnv env, Item item) {
        final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (var == 2) {
				giveQuestItem(env, 182212219, 1);
                return HandlerResult.fromBoolean(useQuestItem(env, item, 2, 3, false));
            }
        }
        return HandlerResult.FAILED;
    }
}