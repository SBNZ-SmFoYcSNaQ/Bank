import axios from "axios";
import { useState } from "react";

function usePost<TPayload, TResponse>(url: string, payload: TPayload, setData: any) {
  const [pending, setPending] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const post = async () : Promise<TResponse | null> => {
    try {
      setPending(true);
      setError(null);
      const res = await axios.post(url, payload);
      if (!res || !res.status.toString().startsWith('2')) {
        console.log("Error posting data");
        setError("Error posting data");
        return null;
      }
      return res.data;
    } catch (err: any) {
      console.log(err.toString());
      setError(err.toString());
      return null;
    } finally {
      setPending(false);
    }
  }

  return {
    pending,
    error,
    post
  }
}

export default usePost;