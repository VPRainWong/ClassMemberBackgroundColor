package com.vp.plugin.sample.classmemberbackground.actions;

import java.awt.Color;

import com.vp.plugin.ApplicationManager;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPActionController;
import com.vp.plugin.diagram.IClassDiagramUIModel;
import com.vp.plugin.diagram.ICompartmentColorModel;
import com.vp.plugin.diagram.IDiagramElement;
import com.vp.plugin.diagram.IDiagramUIModel;
import com.vp.plugin.diagram.IShapeTypeConstants;
import com.vp.plugin.diagram.shape.IClassUIModel;
import com.vp.plugin.model.IAttribute;
import com.vp.plugin.model.IClass;

public class ClassMemberBackgroundActionControl implements VPActionController {

	@Override
	public void performAction(VPAction arg0) {
		// Obtain active diagram
		IDiagramUIModel diagram = ApplicationManager.instance().getDiagramManager().getActiveDiagram();
		// If it is a class diagram then retrieve the class shape form the diagram
		if (diagram instanceof IClassDiagramUIModel) {
			IDiagramElement[] diagramElements = diagram.toDiagramElementArray(IShapeTypeConstants.SHAPE_TYPE_CLASS);
			if (diagramElements != null) {
				// Walk through the class shape to obtain its model element,
				// and then retrieve its attributes
				for (IDiagramElement element : diagramElements) {
					if (element instanceof IClassUIModel) {
						IClass classModel = (IClass) element.getModelElement();
						IAttribute[] attributes = classModel.toAttributeArray();
						for (int i = 0; i < attributes.length; i++) {
							// Change background color for odd number attributes
							if (i%2 == 0) {
								changeColor((IClassUIModel) element, attributes[i]);
							}
						}
					}
				}				
			}
		}
	}
	
	private void changeColor(IClassUIModel classUIModel, IAttribute attribute) {
		// Obtain the compartment color model of the attribute form class model
		// and change its color
		ICompartmentColorModel colorModel = classUIModel.getCompartmentColorModel(attribute, true);
		Color currentColor = colorModel.getBackground();
		if (currentColor == null) {
			colorModel.setBackground(Color.CYAN);			
		} else{
			if (currentColor.equals(Color.CYAN)) {
				colorModel.setBackground(Color.MAGENTA);
			} else {
				colorModel.setBackground(Color.CYAN);
			}
		}
	}

	@Override
	public void update(VPAction arg0) {
		// TODO Auto-generated method stub
		
	}

}
