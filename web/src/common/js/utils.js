/**
 * Created by john on 2017/7/7.
 */
/* eslint-disable */
export const treeData = function (setting, sNodes) {
  let i, l,
    key = setting.idKey,
    parentKey = setting.pIdKey,
    childKey = setting.children;
  if (!key || key === '' || !sNodes) return [];

  if (Object.prototype.toString.call(sNodes) === '[object Array]') {
    let r = [];
    let tmpMap = {};
    for (i = 0, l = sNodes.length; i < l; i++) {
      tmpMap[sNodes[i][key]] = sNodes[i];
    }
    for (i = 0, l = sNodes.length; i < l; i++) {
      if (tmpMap[sNodes[i][parentKey]] && sNodes[i][key] !== sNodes[i][parentKey]) {
        if (!tmpMap[sNodes[i][parentKey]][childKey])
          tmpMap[sNodes[i][parentKey]][childKey] = [];
        tmpMap[sNodes[i][parentKey]][childKey].push(sNodes[i]);
      } else {
        r.push(sNodes[i]);
      }
    }
    return r;
  } else {
    return [sNodes];
  }
};

export function synFlagHandler(synFlag) {
  // 1同步成功 2新增失败 3更新失败 4删除失败
  switch (synFlag) {
    case '1':
      synFlag = '同步成功';
      break;
    case '2':
      synFlag = '新增失败';
      break;
    case '3':
      synFlag = '更新失败';
      break;
    case '4':
      synFlag = '删除失败';
      break;
  }
  return synFlag;
}
