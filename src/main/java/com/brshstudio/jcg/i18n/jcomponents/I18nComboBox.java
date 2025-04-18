package com.brshstudio.jcg.i18n.jcomponents;

import com.brshstudio.jcg.i18n.I18nUtil;
import com.brshstudio.jcg.i18n.LocaleChangeListener;
import com.brshstudio.jcg.i18n.patch.I18nItem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import javax.swing.*;
import java.util.Vector;

public class I18nComboBox<T> extends JComboBox<T> implements LocaleChangeListener {

    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    private ComboBoxModel<T> rawModelI18nKey;

    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    private T selectItemI18nKey;

    public I18nComboBox(ComboBoxModel<T> aModel) {
        super(createI18nModel(aModel));
        setRawModelI18nKey(aModel);
    }

    public I18nComboBox(T[] items) {
        this(createI18nArray(items));
    }

    public I18nComboBox(Vector<T> items) {
        this(createI18nVector(items));
    }

    public I18nComboBox() {
    }

    @Override
    public void onLocaleChanged() {
        // 刷新模型和选中项
        setModel(createI18nModel(getRawModelI18nKey()));
        setSelectedItem(getSelectedItem());
    }

    @Override
    public void setModel(ComboBoxModel<T> aModel) {
        super.setModel(createI18nModel(aModel));
        setRawModelI18nKey(aModel);
    }

    private static <T> ComboBoxModel<T> createI18nModel(ComboBoxModel<T> model) {
        DefaultComboBoxModel<T> newModel = new DefaultComboBoxModel<>();
        for (int i = 0; i < model.getSize(); i++) {
            T item = model.getElementAt(i);
            newModel.addElement(convertItem(item));
        }
        return newModel;
    }

    private static <T> ComboBoxModel<T> createI18nArray(T[] items) {
        DefaultComboBoxModel<T> newModel = new DefaultComboBoxModel<>();
        for (T item : items) {
            newModel.addElement(item);
        }
        return newModel;
    }

    private static <T> ComboBoxModel<T> createI18nVector(Vector<T> items) {
        DefaultComboBoxModel<T> newModel = new DefaultComboBoxModel<>();
        for (T item : items) {
            newModel.addElement(item);
        }
        return newModel;
    }

    @SuppressWarnings("unchecked")
    private static <T> T convertItem(T item) {
        if (item instanceof String) {
            return (T) I18nUtil.get((String) item);
        }
        if (item instanceof I18nItem i18nItem) {
            return (T) I18nUtil.get(i18nItem.getName());
        }
        return item;
    }

    @Override
    public void setSelectedItem(Object anObject) {
        // 保留原始值用于国际化转换
        super.setSelectedItem(convertItem((T) anObject));
        setSelectItemI18nKey((T) anObject);
    }
}
