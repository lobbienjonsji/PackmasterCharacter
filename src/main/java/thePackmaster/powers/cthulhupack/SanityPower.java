package thePackmaster.powers.cthulhupack;

import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thePackmaster.cards.cthulhupack.Lunacy;
import thePackmaster.powers.AbstractPackmasterPower;
import thePackmaster.stances.cthulhupack.NightmareStance;
import thePackmaster.util.Wiz;

import java.util.Objects;

import static thePackmaster.SpireAnniversary5Mod.makeID;

public class SanityPower extends AbstractPackmasterPower {
    public static final String POWER_ID = makeID("SanityPower");
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    public SanityPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.DEBUFF, false, owner, amount);
        canGoNegative = true;
    }

    @Override
    public void atStartOfTurnPostDraw() {
        //The power string reads "if you have negative sanity" for flavor.  Since there is no positive sanity,
        //Amount check is technically unnecessary but who knows, maybe someone down the road wants to make a positive sanity pack.
        if (amount < 0) {
            this.fontScale = 8.0F;
            this.amount -= 1;
            updateDescription();
            AbstractDungeon.onModifyPower();
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        while (amount <= -5) {
            amount += 5;
            flash();
            Wiz.atb(new MakeTempCardInHandAction(new Lunacy()));
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer){
            if (amount == 0){
                removeThis();
            }
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}
