package hillbillies.model;

import hillbillies.model.expressions.EnemyExpression;
import hillbillies.model.expressions.Expression;
import hillbillies.model.expressions.UnitExpression;
import hillbillies.model.world.WorldException;

public class testing {

	public static void main(String[] args) throws WorldException, SyntaxException {
		EnemyExpression enemy = new EnemyExpression();
		System.out.println(enemy instanceof EnemyExpression);
		System.out.println(enemy instanceof UnitExpression);
		System.out.println(enemy instanceof Expression);
		Expression<?> expr = (Expression<?>) enemy;
		System.out.println(expr instanceof EnemyExpression);
		System.out.println(expr instanceof UnitExpression);
		System.out.println(expr instanceof Expression);
		
		
	}

}
