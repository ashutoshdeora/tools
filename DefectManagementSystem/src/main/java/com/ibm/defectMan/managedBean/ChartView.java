package com.ibm.defectMan.managedBean;

import javax.annotation.PostConstruct;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.ChartSeries;
 
@ManagedBean
@ViewScoped
public class ChartView implements Serializable {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BarChartModel barModel;
    private HorizontalBarChartModel horizontalBarModel;
 
    @PostConstruct
    public void init() {
        createBarModels();
    }
 
    public BarChartModel getBarModel() {
        return barModel;
    }
     
    public HorizontalBarChartModel getHorizontalBarModel() {
        return horizontalBarModel;
    }
 
    public void createBarModels() {
        createBarModel();
      //  createHorizontalBarModel();
    }
	
	private void createBarModel() {
        barModel = initBarModel();
         
        barModel.setTitle("Bar Chart");
        barModel.setLegendPosition("ne");
        barModel = initBarModel();
         
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Defects");
         
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("No of Defects per month");
        yAxis.setMin(0);
        yAxis.setMax(100);
        
    }
     
	private void createHorizontalBarModel() {
        horizontalBarModel = new HorizontalBarChartModel();
 
        ChartSeries critical = new ChartSeries();
        critical.setLabel("Critical");
        critical.set("Jan", 50);
        critical.set("Feb", 96);
        critical.set("March", 44);
        critical.set("April", 55);
        critical.set("May", 25);
 
        ChartSeries showStopper = new ChartSeries();
        showStopper.setLabel("ShowStopper");
        showStopper.set("Jan", 2);
        showStopper.set("Feb", 10);
        showStopper.set("March", 8);
        showStopper.set("April", 35);
        showStopper.set("Mat", 12);
 
        horizontalBarModel.addSeries(critical);
        horizontalBarModel.addSeries(showStopper);
         
        horizontalBarModel.setTitle("Horizontal and Stacked");
        horizontalBarModel.setLegendPosition("e");
        horizontalBarModel.setStacked(true);
         
        Axis xAxis = horizontalBarModel.getAxis(AxisType.X);
        xAxis.setLabel("No of Defects per month");
        xAxis.setMin(0);
        xAxis.setMax(200);
         
        Axis yAxis = horizontalBarModel.getAxis(AxisType.Y);
        yAxis.setLabel("Defects");        
    }
    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
 
        ChartSeries critical = new ChartSeries();
        critical.setLabel("Critical");
        critical.set("Jan", 50);
        critical.set("Feb", 96);
        critical.set("March", 44);
        critical.set("April", 55);
        critical.set("May", 25);
 
        ChartSeries showStopper = new ChartSeries();
        showStopper.setLabel("ShowStopper");
        showStopper.set("Jan", 2);
        showStopper.set("Feb", 10);
        showStopper.set("March", 8);
        showStopper.set("April", 35);
        showStopper.set("Mat", 12);
 
        model.addSeries(critical);
        model.addSeries(showStopper);
         
        return model;
    }
    
    public void itemSelect(ItemSelectEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected",
                        "Item Index: " + event.getItemIndex() + ", Series Index:" + event.getSeriesIndex());
         
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
 
}
