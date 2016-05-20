package hillbillies.model.expressions;

public abstract class Expression<T> implements IExpression{
	
	
	@Override
	public IExpression[] getExpressions() {
		return new IExpression[]{};
	}
}
