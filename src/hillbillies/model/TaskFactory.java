package hillbillies.model;

import java.util.ArrayList;
import java.util.List;

import hillbillies.model.expressions.AndExpression;
import hillbillies.model.expressions.AnyExpression;
import hillbillies.model.expressions.BooleanExpression;
import hillbillies.model.expressions.BoulderExpression;
import hillbillies.model.expressions.CarriesItemExpression;
import hillbillies.model.expressions.EnemyExpression;
import hillbillies.model.expressions.Expression;
import hillbillies.model.expressions.FalseExpression;
import hillbillies.model.expressions.FriendExpression;
import hillbillies.model.expressions.HereExpression;
import hillbillies.model.expressions.IsAliveExpression;
import hillbillies.model.expressions.IsEnemyExpression;
import hillbillies.model.expressions.IsFriendExpression;
import hillbillies.model.expressions.IsPassableExpression;
import hillbillies.model.expressions.IsSolidExpression;
import hillbillies.model.expressions.LiteralPositionExpression;
import hillbillies.model.expressions.LogExpression;
import hillbillies.model.expressions.NextToExpression;
import hillbillies.model.expressions.NotExpression;
import hillbillies.model.expressions.OrExpression;
import hillbillies.model.expressions.PositionExpression;
import hillbillies.model.expressions.PositionOfExpression;
import hillbillies.model.expressions.ReadVariableExpression;
import hillbillies.model.expressions.SelectedExpression;
import hillbillies.model.expressions.ThisExpression;
import hillbillies.model.expressions.TrueExpression;
import hillbillies.model.expressions.UnitExpression;
import hillbillies.model.expressions.WorkshopExpression;
import hillbillies.model.hillbilliesobject.unit.UnitException;
import hillbillies.model.scheduler.Task;
import hillbillies.model.statement.AssignStatement;
import hillbillies.model.statement.AttackStatement;
import hillbillies.model.statement.BreakStatement;
import hillbillies.model.statement.FollowStatement;
import hillbillies.model.statement.IfThenElseStatement;
import hillbillies.model.statement.MoveStatement;
import hillbillies.model.statement.MultipleStatement;
import hillbillies.model.statement.PrintStatement;
import hillbillies.model.statement.Statement;
import hillbillies.model.statement.WhileStatement;
import hillbillies.model.statement.WorkStatement;
import hillbillies.model.world.WorldException;
import hillbillies.part3.programs.ITaskFactory;
import hillbillies.part3.programs.SourceLocation;

public class TaskFactory implements ITaskFactory<Expression<?>, Statement, Task> {

	@Override
	public List<Task> createTasks(String name, int priority, Statement activity, List<int[]> selectedCubes) {
		List<Task> tasks = new ArrayList<>();
		try{
			if (selectedCubes.size()==0){
				tasks.add(new Task(name, priority, activity, null));
			}
			for (int[] i: selectedCubes){
				Position pos = new Position(i[0], i[1], i[2], null);
				tasks.add(new Task(name, priority, activity, pos));
			}
		} catch (Exception e){
		}
		return tasks;
	}

	@Override
	public Statement createAssignment(String variableName, Expression<?> value, SourceLocation sourceLocation) {
		try {
			return new AssignStatement(variableName, value);
		} catch (WorldException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Statement createWhile(Expression<?> condition, Statement body, SourceLocation sourceLocation) {
		try {
			return new WhileStatement<BooleanExpression>((BooleanExpression) condition, body);
		} catch (WorldException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Statement createIf(Expression<?> condition, Statement ifBody, Statement elseBody,
			SourceLocation sourceLocation) {
		try {
			return new IfThenElseStatement<BooleanExpression>((BooleanExpression)condition,ifBody,elseBody);
		} catch (WorldException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Statement createBreak(SourceLocation sourceLocation) {
		return new BreakStatement();
	}

	@Override
	public Statement createPrint(Expression<?> value, SourceLocation sourceLocation) {
		try{
		if (value instanceof BooleanExpression)
			return new PrintStatement<BooleanExpression>((BooleanExpression) value);
		if (value instanceof PositionExpression)
			return new PrintStatement<PositionExpression>((PositionExpression) value);
		if (value instanceof UnitExpression)
			return new PrintStatement<UnitExpression>((UnitExpression) value);
		} catch (WorldException w){
			w.printStackTrace();
		}
		return null;


	}

	@Override
	public Statement createSequence(List<Statement> statements, SourceLocation sourceLocation) {
		try {
			return new MultipleStatement(statements);
		} catch (WorldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Statement createMoveTo(Expression<?> position, SourceLocation sourceLocation) {
		
		try{
			if (position instanceof ReadVariableExpression){
				return new MoveStatement<ReadVariableExpression>((ReadVariableExpression) position);
			}
			return new MoveStatement<PositionExpression>((PositionExpression) position);
		} catch (WorldException s){
			s.printStackTrace();
		}
		return null;
	}

	@Override
	public Statement createWork(Expression<?> position, SourceLocation sourceLocation) {
		try {
			return new WorkStatement<PositionExpression>((PositionExpression) position);
		} catch (WorldException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Statement createFollow(Expression<?> unit, SourceLocation sourceLocation) {
		try {
			System.out.println("follow statement created");
			return new FollowStatement<UnitExpression>((UnitExpression) unit);
		} catch (WorldException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Statement createAttack(Expression<?> unit, SourceLocation sourceLocation) {
		try {
			return new AttackStatement<UnitExpression>((UnitExpression) unit);
		} catch (WorldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Expression<?> createReadVariable(String variableName, SourceLocation sourceLocation) {
		try {
			return new ReadVariableExpression(variableName);
		} catch (WorldException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Expression<?> createIsSolid(Expression<?> position, SourceLocation sourceLocation) {
		try {
			return new IsSolidExpression<PositionExpression>((PositionExpression) position);
		} catch (WorldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Expression<?> createIsPassable(Expression<?> position, SourceLocation sourceLocation) {
		try {
			return new IsPassableExpression<PositionExpression>((PositionExpression)position);
		} catch (WorldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Expression<?> createIsFriend(Expression<?> unit, SourceLocation sourceLocation) {
		try {
			return new IsFriendExpression<UnitExpression>((UnitExpression) unit);
		} catch (WorldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Expression<?> createIsEnemy(Expression<?> unit, SourceLocation sourceLocation) {
		try {
			return new IsEnemyExpression<UnitExpression>((UnitExpression) unit);
		} catch (WorldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Expression<?> createIsAlive(Expression<?> unit, SourceLocation sourceLocation) {
		try {
			return new IsAliveExpression<UnitExpression>((UnitExpression) unit);
		} catch (WorldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Expression<?> createCarriesItem(Expression<?> unit, SourceLocation sourceLocation) {
		try {
			return new CarriesItemExpression<UnitExpression>((UnitExpression) unit);
		} catch (WorldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Expression<?> createNot(Expression<?> expression, SourceLocation sourceLocation) {
		try {
			return new NotExpression<BooleanExpression>((BooleanExpression)expression);
		} catch (WorldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Expression<?> createAnd(Expression<?> left, Expression<?> right, SourceLocation sourceLocation) {
		try {
			return new AndExpression<BooleanExpression,BooleanExpression>((BooleanExpression)left,(BooleanExpression)right);
		} catch (WorldException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Expression<?> createOr(Expression<?> left, Expression<?> right, SourceLocation sourceLocation) {
		try {
			return new OrExpression<BooleanExpression,BooleanExpression>((BooleanExpression)left,(BooleanExpression)right);
		} catch (WorldException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Expression<?> createHerePosition(SourceLocation sourceLocation) {
		return new HereExpression();
	}

	@Override
	public Expression<?> createLogPosition(SourceLocation sourceLocation) {
		return new LogExpression();
	}

	@Override
	public Expression<?> createBoulderPosition(SourceLocation sourceLocation) {
		return new BoulderExpression();
	}

	@Override
	public Expression<?> createWorkshopPosition(SourceLocation sourceLocation) {
		return new WorkshopExpression();
	}

	@Override
	public Expression<?> createSelectedPosition(SourceLocation sourceLocation) {
		return new SelectedExpression();
	}

	@Override
	public Expression<?> createNextToPosition(Expression<?> position, SourceLocation sourceLocation) {
		try {
			return new NextToExpression<PositionExpression>((PositionExpression)position);
		} catch (WorldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}

	@Override
	public Expression<?> createLiteralPosition(int x, int y, int z, SourceLocation sourceLocation) {
		//try {
			return new LiteralPositionExpression(x,y,z);
	//	} catch (UnitException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		//return null;
	}

	@Override
	public Expression<?> createThis(SourceLocation sourceLocation) {
		return new ThisExpression();
	}

	@Override
	public Expression<?> createFriend(SourceLocation sourceLocation) {
		return new FriendExpression();
	}

	@Override
	public Expression<?> createEnemy(SourceLocation sourceLocation) {
		return new EnemyExpression();
	}

	@Override
	public Expression<?> createAny(SourceLocation sourceLocation) {
		return new AnyExpression();
	}

	@Override
	public Expression<?> createTrue(SourceLocation sourceLocation) {
		return new TrueExpression();
	}

	@Override
	public Expression<?> createFalse(SourceLocation sourceLocation) {
		return new FalseExpression();
	}

	@Override
	public Expression<?> createPositionOf(Expression<?> unit, SourceLocation sourceLocation) {
		try {
			return new PositionOfExpression<UnitExpression>((UnitExpression)unit);
		} catch (WorldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}



