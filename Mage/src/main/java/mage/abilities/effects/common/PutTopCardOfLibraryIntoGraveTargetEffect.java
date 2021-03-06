/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author LevelX2
 */
public class PutTopCardOfLibraryIntoGraveTargetEffect extends OneShotEffect {

    private DynamicValue numberCards;

    public PutTopCardOfLibraryIntoGraveTargetEffect(int numberCards) {
        this(new StaticValue(numberCards));
    }

    public PutTopCardOfLibraryIntoGraveTargetEffect(DynamicValue numberCards) {
        super(Outcome.Discard);
        this.numberCards = numberCards;
        this.staticText = setText();
    }

    public PutTopCardOfLibraryIntoGraveTargetEffect(final PutTopCardOfLibraryIntoGraveTargetEffect effect) {
        super(effect);
        this.numberCards = effect.numberCards;
    }

    @Override
    public PutTopCardOfLibraryIntoGraveTargetEffect copy() {
        return new PutTopCardOfLibraryIntoGraveTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            return player.moveCards(player.getLibrary().getTopCards(game, numberCards.calculate(game, source, this)), Zone.GRAVEYARD, source, game);
        }
        return false;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("Target player puts the top ");
        if (numberCards.toString().equals("1")) {
            sb.append(" card");
        } else {
            sb.append(CardUtil.numberToText(numberCards.toString()));
            sb.append(" cards");
        }
        sb.append(" of his or her library into his or her graveyard");
        return sb.toString();
    }
}
