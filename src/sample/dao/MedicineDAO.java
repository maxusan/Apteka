package sample.dao;

import javafx.collections.ObservableList;
import sample.domain.Medicine;

public interface MedicineDAO {
    Medicine getMedicineById(long id);
    boolean insertMedicine(Medicine medicine);
    boolean deleteMedicine(long id);
    boolean updateMedicine(Medicine medicine);
    Medicine getFirstMedicine();
    ObservableList<Medicine> getAllMedicines();

}
