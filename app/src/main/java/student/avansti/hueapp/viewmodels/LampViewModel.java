package student.avansti.hueapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import student.avansti.hueapp.data.DLamp;
import student.avansti.hueapp.parts.PartPhilipsHue;

public class LampViewModel extends ViewModel {

    private final MutableLiveData<List<DLamp>> lampList = new MutableLiveData<>();
    private final MutableLiveData<DLamp> selectedLamp = new MutableLiveData<>();

    private Thread refreshThread;

    public LiveData<List<DLamp>> getLamps() {
        if(this.lampList.getValue() == null) {
            this.lampList.setValue(new ArrayList<>());
            this.refreshLamps();
        }
        return this.lampList;
    }

    public void setLamps(List<DLamp> lampList) {
        if(lampList != null) this.lampList.setValue(lampList);
        else this.lampList.setValue(new ArrayList<>());
    }

    public LiveData<DLamp> getSelectedLamp() {
        return this.selectedLamp;
    }

    public void select(DLamp lamp) {
        this.selectedLamp.setValue(lamp);
    }

    public void refreshLamps() {

        if(this.refreshThread == null) {
            this.refreshThread = new Thread(() -> {
                this.lampList.postValue(PartPhilipsHue.getInstance().getLamps());
                this.refreshThread = null;
            });
            this.refreshThread.start();
        }

    }

}
