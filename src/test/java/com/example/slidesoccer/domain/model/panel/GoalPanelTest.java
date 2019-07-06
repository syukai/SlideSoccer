package com.example.slidesoccer.domain.model.panel;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.slidesoccer.domain.model.move.Move;
import com.example.slidesoccer.domain.model.space.Space;
import com.example.slidesoccer.domain.model.space.Spaces;
import com.example.slidesoccer.domain.type.position.Position;
import com.example.slidesoccer.domain.type.position.X;
import com.example.slidesoccer.domain.type.position.Y;

class GoalPanelTest {

	@Test
	void canMove() {

		GoalPanel goal = new GoalPanel(new Position(X.of(1), Y.of(3)));

		Space space1_1 = new Space(new Position(X.of(1), Y.of(1)));
		Space space2_2 = new Space(new Position(X.of(2), Y.of(2)));
		Spaces spaces = new Spaces(space1_1, space2_2);
		
		List<Move> moves = goal.getCanMove(spaces);
		
		assertEquals(0, moves.size());
	}

}
