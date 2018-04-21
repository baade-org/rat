package org.baade.rat.core.context;

public interface IResponse extends IParameter{

    IParameter getParameter();

    IRequest reverse();
}
