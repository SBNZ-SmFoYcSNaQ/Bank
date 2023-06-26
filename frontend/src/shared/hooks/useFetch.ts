import { useEffect, useState } from "react";

function useFetch<T>(url: string) {
  const [loading, setLoading] = useState(false);
  const [data, setData] = useState<T | null>(null);  
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      setError(null);
      try {
        const res = await fetch(url);
        if (!res.ok) {
          console.log("error fetching data");
          setError("error fetching data");
        }
        const resData = await res.json();
        setData(resData);
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
    error
  }
}

export default useFetch;