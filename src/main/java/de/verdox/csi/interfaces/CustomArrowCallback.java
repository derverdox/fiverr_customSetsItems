package de.verdox.csi.interfaces;

import de.verdox.csi.model.CustomArrow;

@FunctionalInterface
public interface CustomArrowCallback {
    void callback(CustomArrow customArrow);
}
