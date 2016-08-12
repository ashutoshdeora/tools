package com.ibm.utils;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.ibm.entity.DatasetMaster;
import com.ibm.managedBean.DataSetRunManageBean;

@FacesConverter("dataSetConverter")
public class DataSetConverter implements Converter{
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {
            try {
            	DataSetRunManageBean service = (DataSetRunManageBean) fc.getExternalContext().getApplicationMap().get("dataSetRunManageBean");
                return service.getDatasetmastersList().get(Integer.parseInt(value));
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        }
        else {
            return null;
        }
    }
 
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((DatasetMaster) object).getDatasetname());
        }
        else {
            return null;
        }
    }   
}
