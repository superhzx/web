/**
 * Created by alan on 2017/7/2.
 */
import {
  ADD_TAB,
  REMOVE_TAB,
  CHANGE_SELECT_TAB,
  TOGGLE_SIDEBAR
} from './mutation-types';
export default {
  [ADD_TAB](state, {
    name,
    title
  }) {
    for (let i = 0; i < state.editableTabs.length; i++) {
      if (state.editableTabs[i].name === name) {
        state.editableTabsValue = name;
        return;
      }
    }
    state.editableTabs.push({
      title: title,
      name: name,
      content: title
    });
    state.editableTabsValue = name;
  },
  [REMOVE_TAB](state, {name}) {
    let tabs = state.editableTabs;
    let activeName = state.editableTabsValue;
    if (activeName === name) {
      tabs.forEach((tab, index) => {
        if (tab.name === name) {
          let nextTab = tabs[index + 1] || tabs[index - 1];
          if (nextTab) {
            activeName = nextTab.name;
          }
        }
      });
    }

    state.editableTabsValue = activeName;
    state.editableTabs = tabs.filter(tab => tab.name !== name);
  },
  [CHANGE_SELECT_TAB](state, {name}) {
    state.editableTabsValue = name;
  },
  [TOGGLE_SIDEBAR](state) {
    state.sidebar.opened = !state.sidebar.opened;
  }
}
;
