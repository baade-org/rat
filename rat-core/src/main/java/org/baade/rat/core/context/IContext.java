package org.baade.rat.core.context;

public interface IContext extends IParameter {

    IParameter getParameter();


    IRequest getRequest();

    IResponse getResponse();
}
