package com.example.slidesoccer.domain.model.field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.slidesoccer.domain.model.move.Move;
import com.example.slidesoccer.domain.model.move.Moves;
import com.example.slidesoccer.domain.model.panel.GoalPanel;
import com.example.slidesoccer.domain.model.panel.SmallPanel;
import com.example.slidesoccer.domain.model.panel.WidePanel;
import com.example.slidesoccer.domain.type.position.Position;
import com.example.slidesoccer.domain.type.position.X;
import com.example.slidesoccer.domain.type.position.Y;

class FieldTest {
	FieldBuilder builder;
	@BeforeEach
	void beforeEach() {
		// SSTT
		//   TT
		// GGSS
		// GGWW
		// SSWW
		builder = Field.builder()
				.small(X.of(1), Y.of(1))
				.small(X.of(2), Y.of(1))
				.tall(X.of(3), Y.of(1))
				.tall(X.of(4), Y.of(1))
				.goal(X.of(1), Y.of(3))
				.small(X.of(3), Y.of(3))
				.small(X.of(4), Y.of(3))
				.wide(X.of(3), Y.of(4))
				.small(X.of(1), Y.of(5))
				.small(X.of(2), Y.of(5))
				.wide(X.of(3), Y.of(5));
	}
	
	@Test
	@DisplayName("ゴールの位置が2-4ならゴール")
	void isGoal() {
		Field field = builder
				.goal(X.of(2), Y.of(4))
				.create();
		
		assertTrue(field.isGoal());
	}
	
	@Test
	@DisplayName("ゴールの位置が2-4でなければゴールじゃない")
	void isNotGoal() {
		Field field = builder
				.goal(X.of(2), Y.of(3))
				.create();
		
		assertFalse(field.isGoal());
	}
	
	@Test
	@DisplayName("スペースを２つ持つ")
	void hasTwoSpaces() {
		Field field = builder.create();
		
		assertTrue(field.hasSpace(new Position(X.of(1), Y.of(2))));
		assertTrue(field.hasSpace(new Position(X.of(2), Y.of(2))));
		
	}
	
	@Test
	@DisplayName("可能な動作の一覧を取得する")
	void getCanMoves() {
		Field field = builder.create();
		
		Moves moves = field.getCanMoves();
		assertEquals(3, moves.size());
		assertTrue(moves.containsSource(new SmallPanel(new Position(X.of(1), Y.of(1)))));
		assertTrue(moves.containsSource(new SmallPanel(new Position(X.of(2), Y.of(1)))));
		assertTrue(moves.containsSource(new GoalPanel(new Position(X.of(1), Y.of(3)))));
		assertFalse(moves.containsSource(new WidePanel(new Position(X.of(1), Y.of(1)))));
	}
	
	@Test
	@DisplayName("SmallPanelをMove")
	void MoveSmallPanel() {
		Field field = builder.create();

		//  STT
		// S TT
		// GGSS
		// GGWW
		// SSWW
		Move move = new Move(new SmallPanel(new Position(X.of(1), Y.of(1)))
				, new Position(X.of(1), Y.of(2)));
		
		field.move(move);
		
		Moves moves2 = field.getCanMoves();
		
		// 移動できるヶ所は4ヶ所あるが、手戻しはしないので3ヶ所
		assertEquals(3, moves2.size());
		
		assertTrue(moves2.contains(new Move(new SmallPanel(new Position(X.of(2), Y.of(1)))
				, new Position(X.of(2), Y.of(2)))
				));
		assertFalse(moves2.contains(new Move(new SmallPanel(new Position(X.of(1), Y.of(2)))
				, new Position(X.of(1), Y.of(1)))
				));
		assertTrue(moves2.contains(new Move(new SmallPanel(new Position(X.of(1), Y.of(2)))
				, new Position(X.of(2), Y.of(2)))
				));
		assertTrue(moves2.contains(new Move(new SmallPanel(new Position(X.of(2), Y.of(1)))
				, new Position(X.of(1), Y.of(1)))
				));
		
	}

	@Test
	@DisplayName("ハッシュ値で同じ盤面か判断する")
	void HashEquals() {
		Field field = builder.create();
		Field field2 = builder.create();
		
		assertEquals(field.hashCode(), field2.hashCode());

		// 一方をMoveしたら一致しない
		Move move = new Move(new SmallPanel(new Position(X.of(1), Y.of(1)))
				, new Position(X.of(1), Y.of(2)));
		field.move(move);
		assertNotEquals(field.hashCode(), field2.height.hashCode());
		
		// 同じようにMoveしたら一致する
		field2.move(move);
		assertEquals(field.hashCode(), field2.hashCode());
	}
	
	@Test
	@DisplayName("1回Moveして1回Undo")
	void Undo() {
		Field field = builder.create();
		Field field2 = builder.create();

		//  STT
		// S TT
		// GGSS
		// GGWW
		// SSWW
		Move move = new Move(new SmallPanel(new Position(X.of(1), Y.of(1)))
				, new Position(X.of(1), Y.of(2)));
		
		field.move(move);
		
		assertNotEquals(field2.hashCode(), field.hashCode());
		
		field.undo();
		
		assertEquals(field2.hashCode(), field.hashCode());
	}
	
}
