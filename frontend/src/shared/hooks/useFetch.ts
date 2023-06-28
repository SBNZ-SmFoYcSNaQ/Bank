import axios from "axios";
import { useEffect, useState } from "react";

function useFetch<T extends object>(url: string) {
  const [loading, setLoading] = useState(false);
  const [data, setData] = useState<T | null>(null);  
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      setError(null);
      try {
        const res = await axios.get<T>(url);
        if (!res || !res.status.toString().startsWith('2')) {
          console.log("error fetching data");
          setError("error fetching data");
          return;
        }
        setData(res.data);
      } catch (err: any) {
        console.log(err.toString());
        setError(err.toString());
      } finally {
        setLoading(false);
      }
    } 
    
    fetchData();
  }, [url])

  return {
    loading,
    data,
    setData,
    error
  }
}

export default useFetch;