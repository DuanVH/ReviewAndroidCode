package com.example.gem.reviewandroidcode.pojo.model;

import com.example.gem.reviewandroidcode.pojo.dto.WeatherCountryDTO;
import com.example.gem.reviewandroidcode.pojo.dto.WeatherDTO;

import java.util.ArrayList;
import java.util.List;

public class WeatherCountryModel {
  public CoordModel coordModel;
  public List<WeatherModel> weatherModels;
  public String base;
  public MainModel mainModel;
  public long visibility;
  public WindModel windModel;
  public CloudsModel cloudsModel;
  public long dt;
  public SysModel sysModel;
  public long id;
  public String name;
  public int cod;

  public WeatherCountryModel() {
    coordModel = new CoordModel();
    weatherModels = new ArrayList<>();
    mainModel = new MainModel();
    windModel = new WindModel();
    cloudsModel = new CloudsModel();
    sysModel = new SysModel();
  }

  public void convert(WeatherCountryDTO dto) {
    if (dto.coordDTO != null)
      coordModel.convert(dto.coordDTO);

    if (dto.weatherDTOS != null && !dto.weatherDTOS.isEmpty())
      for (WeatherDTO item : dto.weatherDTOS) {
        WeatherModel model = new WeatherModel();
        model.convert(item);
        weatherModels.add(model);
      }

//      if (base != null && !)
  }

  public CoordModel getCoordModel() {
    return coordModel;
  }

  public void setCoordModel(CoordModel coordModel) {
    this.coordModel = coordModel;
  }

  public List<WeatherModel> getWeatherModels() {
    return weatherModels;
  }

  public void setWeatherModels(List<WeatherModel> weatherModels) {
    this.weatherModels = weatherModels;
  }

  public String getBase() {
    return base;
  }

  public void setBase(String base) {
    this.base = base;
  }

  public MainModel getMainModel() {
    return mainModel;
  }

  public void setMainModel(MainModel mainModel) {
    this.mainModel = mainModel;
  }

  public long getVisibility() {
    return visibility;
  }

  public void setVisibility(long visibility) {
    this.visibility = visibility;
  }

  public WindModel getWindModel() {
    return windModel;
  }

  public void setWindModel(WindModel windModel) {
    this.windModel = windModel;
  }

  public CloudsModel getCloudsModel() {
    return cloudsModel;
  }

  public void setCloudsModel(CloudsModel cloudsModel) {
    this.cloudsModel = cloudsModel;
  }

  public long getDt() {
    return dt;
  }

  public void setDt(long dt) {
    this.dt = dt;
  }

  public SysModel getSysModel() {
    return sysModel;
  }

  public void setSysModel(SysModel sysModel) {
    this.sysModel = sysModel;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getCod() {
    return cod;
  }

  public void setCod(int cod) {
    this.cod = cod;
  }
}
