package com.github.mauricioaniche.ck.metric;

import com.github.mauricioaniche.ck.CKClassResult;
import com.github.mauricioaniche.ck.CKMethodResult;
import org.eclipse.jdt.core.dom.*;

public class NumberOfSubClassesLambdasAndAnonymousClasses implements CKASTVisitor, ClassLevelMetric, MethodLevelMetric {

	private int anonymousClassesQty = 0;
	private int subClassesQty = 0;
	private int lambdasQty = 0;

	private String firstFound = null;

	public void visit(TypeDeclaration node) {

		if(firstFound == null)
			firstFound = "type";

		subClassesQty++;
	}

	public void visit(EnumDeclaration node) {
		// we count enum as class declaration!
		subClassesQty++;

		if(firstFound == null)
			firstFound = "enum";
	}

	public void visit(LambdaExpression node) {
		lambdasQty++;

		if(firstFound == null)
			firstFound = "lambda";
	}

	public void visit(AnonymousClassDeclaration node) {
		anonymousClassesQty++;

		if(firstFound == null)
			firstFound = "anonymous";
	}

	@Override
	public void setResult(CKClassResult result) {
		result.setAnonymousClassesQty(anonymousClassesQty - (firstFound.equals("anonymous")?1:0));
		result.setSubClassesQty(subClassesQty - (firstFound.equals("type") || firstFound.equals("enum")?1:0));
		result.setLambdasQty(lambdasQty - (firstFound.equals("lambda")?1:0));

	}

	@Override
	public void setResult(CKMethodResult result) {
		result.setAnonymousClassesQty(anonymousClassesQty);
		result.setSubClassesQty(subClassesQty);
		result.setLambdasQty(lambdasQty);
	}
}
