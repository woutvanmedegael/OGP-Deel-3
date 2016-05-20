package hillbillies.model.task;
import java.util.ArrayList;

import hillbillies.model.expressions.IExpression;
import hillbillies.model.expressions.ReadVariableExpression;
import hillbillies.model.expressions.SelectedExpression;
import hillbillies.model.statement.ActionStatement;
import hillbillies.model.statement.AssignStatement;
import hillbillies.model.statement.BreakStatement;
import hillbillies.model.statement.IfThenElseStatement;
import hillbillies.model.statement.MultipleStatement;
import hillbillies.model.statement.PrintStatement;
import hillbillies.model.statement.Statement;
import hillbillies.model.statement.WhileStatement;

public class SubType {
	/*
	 * 1 : ActionStatement
	 * 2 : AssignStatement
	 * 3 : BreakStatement
	 * 4 : IfThenElseStatement
	 * 5 : MultipleStatement
	 * 6 : WhileStatement
	 * 7 : ReadVariableExpression
	 * 8 : SelectedExpression
	 * 9 : OtherExpression
	 * 10: PrintStatement
	 */
	private int type = 0;
	private final Tree statementTree = new Tree();
	private final Tree expressionTree = new Tree();
	private String variableName;
	
	public void setType(int type){
		this.type = type;
	}
	
	public SubType(Statement stat){
		for (IExpression e: stat.getExpressions()){
			this.addExpressionSubType(new SubType(e));
		}
		for (Statement s: stat.getStatements()){
			this.addStatementSubType(new SubType(s));
		}
		if (stat instanceof ActionStatement){
			this.type = 1;
		}
		else if (stat instanceof AssignStatement){
			this.type = 2;
			this.variableName = ((AssignStatement) stat).getVariableName();
		}
		else if (stat instanceof BreakStatement){
			this.type = 3;
		}
		else if (stat instanceof IfThenElseStatement){
			this.type = 4;
		}
		else if (stat instanceof MultipleStatement){
			this.type = 5;
			for (Statement s: ((MultipleStatement) stat).getStatements()){
				this.statementTree.addSubType(new SubType(s));
			}
			
			}
		else if(stat instanceof WhileStatement){
			this.type = 6;
		}
		else if (stat instanceof PrintStatement){
			this.type = 10;
		}
	}
	
	public SubType(IExpression expr){
		if (expr instanceof ReadVariableExpression){
			this.type = 7;
			this.variableName = ((ReadVariableExpression) expr).getVariableName();
		}
		else if (expr instanceof SelectedExpression){
			this.type = 8;
		}
		else {
			this.type = 9;
			for (IExpression e: expr.getExpressions()){
				this.expressionTree.addSubType(new SubType(e));
			}
		}
	}
	
	private int getType(){
		return this.type;
	}
	
	private void addStatementSubType(SubType sub){
		this.statementTree.addSubType(sub);
	}
	
	private void addExpressionSubType(SubType sub){
		this.expressionTree.addSubType(sub);
	}
	
	public boolean containsUncoveredBreak(){
		if (this.type == 3){
			return true;
		}
		if (this.type == 6){
			return false;
		}
		for (SubType s : this.statementTree.getSubTypes()){
			if (s.getType()!=6 && s.containsUncoveredBreak()){
				return true;
			}
		}
		return false;
	}
	
	public boolean containsSelected(){
		if (this.type == 8){
			return true;
		}
		for (SubType s: this.statementTree.getSubTypes()){
			if (s.containsSelected()){
				return true;
			}
		}
		for (SubType s: this.expressionTree.getSubTypes()){
			if (s.containsSelected()){
				return true;
			}
		}
		return false;
	}
	
	public boolean containsUnassignedVariable(ArrayList<String> variables){
		if (this.type == 7 && !variables.contains(this.variableName)){
			return true;
		}
		else if (this.type == 2){
			variables.add(this.variableName);
		}
		
		for (SubType s: this.expressionTree.getSubTypes()){
			if (s.containsUnassignedVariable(variables)){
				return true;
			}
		}
		
		if (this.getType()==4){
			ArrayList<String> tempVariables1 = new ArrayList<>();
			tempVariables1.addAll(variables);
			ArrayList<String> tempVariables2 = new ArrayList<>();
			tempVariables2.addAll(variables);
			if (this.statementTree.getSubTypes().get(0).containsUnassignedVariable(tempVariables1)){
				return true;
			}
			if (this.statementTree.getSubTypes().get(1).containsUnassignedVariable(tempVariables2)){
				return true;
			}
			tempVariables1.retainAll(tempVariables2);
			variables.retainAll(tempVariables1);
			}

		else if (this.getType()==6){
			ArrayList<String> tempVariables1 = new ArrayList<>();
			tempVariables1.addAll(variables);
			if (this.statementTree.getSubTypes().get(0).containsUnassignedVariable(tempVariables1)){
				return true;
			}
		}
		
		else {
			for (SubType s: this.statementTree.getSubTypes()){
				if (s.containsUnassignedVariable(variables)){
					return true;
				}
			}
		}
		return false;
	}
	
	
}
