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
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreatureOrPlayer;
import mage.filter.predicate.mageobject.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author Quercitron
 */
public class ConeOfFlame extends CardImpl {

    public ConeOfFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}{R}");


        // Cone of Flame deals 1 damage to target creature or player, 2 damage to another target creature or player, and 3 damage to a third target creature or player.
        FilterCreatureOrPlayer filter1 = new FilterCreatureOrPlayer("creature or player to deal 1 damage");
        TargetCreatureOrPlayer target1 = new TargetCreatureOrPlayer(1, 1, filter1);
        target1.setTargetTag(1);
        this.getSpellAbility().addTarget(target1);
        
        FilterCreatureOrPlayer filter2 = new FilterCreatureOrPlayer("another creature or player to deal 2 damage");
        AnotherTargetPredicate predicate2 = new AnotherTargetPredicate(2);
        filter2.getCreatureFilter().add(predicate2);
        filter2.getPlayerFilter().add(predicate2);
        TargetCreatureOrPlayer target2 = new TargetCreatureOrPlayer(1, 1, filter2);
        target2.setTargetTag(2);
        this.getSpellAbility().addTarget(target2);
        
        FilterCreatureOrPlayer filter3 = new FilterCreatureOrPlayer("another creature or player to deal 3 damage");
        AnotherTargetPredicate predicate3 = new AnotherTargetPredicate(3);
        filter3.getCreatureFilter().add(predicate3);
        filter3.getPlayerFilter().add(predicate3);
        TargetCreatureOrPlayer target3 = new TargetCreatureOrPlayer(1, 1, filter3);
        target3.setTargetTag(3);
        this.getSpellAbility().addTarget(target3);
        
        this.getSpellAbility().addEffect(new ConeOfFlameEffect());
    }

    public ConeOfFlame(final ConeOfFlame card) {
        super(card);
    }

    @Override
    public ConeOfFlame copy() {
        return new ConeOfFlame(this);
    }
}

class ConeOfFlameEffect extends OneShotEffect {

    public ConeOfFlameEffect() {
        super(Outcome.Damage);
        this.staticText = "{source} deals 1 damage to target creature or player, 2 damage to another target creature or player, and 3 damage to a third target creature or player";
    }
    
    public ConeOfFlameEffect(final ConeOfFlameEffect effect) {
        super(effect);
    }

    @Override
    public ConeOfFlameEffect copy() {
        return new ConeOfFlameEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        boolean applied = false;
        int damage = 1;
        for (Target target : source.getTargets()) {
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                applied |= (permanent.damage(damage, source.getSourceId(), game, false, true) > 0);
            }
            Player player = game.getPlayer(target.getFirstTarget());
            if (player != null) {
                applied |= (player.damage(damage, source.getSourceId(), game, false, true) > 0);
            }
            damage++;
        }
        return applied;
    }
    
}
