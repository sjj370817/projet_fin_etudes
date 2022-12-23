import React from 'react'
import ContratsTable from '../Components/Contract/ContratsTable';
import { useState, useEffect } from 'react';
import api from "./caramazinlease/src/api/carlease"
import styles from "./All.module.css"

function AllContrats() {
  const [contrats , setContrats] = useState([]);

  useEffect(() => {
    getContrats();
  }, []);

  const getContrats = async () =>{
    try{
      const result =await api.get("/contrats/");
      setContrats(result.data);
    }catch(error){
      console.log(error);
    }
  };
  return (
    <div className={styles.container}><ContratsTable contrats={contrats} refresh={getContrats}/></div>
  )
}

export default AllContrats