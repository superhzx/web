import axios from 'axios';

const address = 'https://localhost/iServer/';
export function listByPage (queryParam) {
  return axios({
    method: 'get',
    url: address + 'user/listByPage',
    params: queryParam
  });
}

export function saveUser (queryParam) {
  return axios({
    method: 'get',
    url: address + 'user/save',
    params: queryParam
  });
}

export function deleteUser (queryParam) {
  return axios({
    method: 'get',
    url: address + 'user/delete',
    params: queryParam
  });
}
