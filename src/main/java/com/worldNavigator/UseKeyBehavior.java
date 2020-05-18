package com.worldNavigator;

import java.util.List;

public interface UseKeyBehavior {
  Boolean getIs_locked();

  void initIs_locked(Boolean isLocked);

  void setIs_locked(Boolean isLocked);

  String getKey();

  void setKey(Key key);

  String useKey(List<KeyChecker> keys);
}
