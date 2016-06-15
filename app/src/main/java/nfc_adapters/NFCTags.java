package nfc_adapters;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import java.io.UnsupportedEncodingException;

public class NFCTags
{
    private NfcAdapter nfcAdapter;
    private Context context;
    private Activity activity;

    public NFCTags(Activity activity, Context context)
    {
        this.context = context;
        this.activity = activity;
        this.nfcAdapter = NfcAdapter.getDefaultAdapter(context);
    }

    public boolean nfcCheck()
    {
        // Enable NFC
        if(nfcAdapter !=null && nfcAdapter.isEnabled())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private String readTextFromMessage(NdefMessage ndefMessage)
    {
        NdefRecord[] ndefRecords = ndefMessage.getRecords();

        if(ndefRecords != null && ndefRecords.length>0)
        {
            NdefRecord ndefRecord = ndefRecords[0];
            String tagContent = getTextFromNdefRecord(ndefRecord);
            return tagContent;
        }
        else
        {
            return "NO TEXT FROM MESSAGE";
        }
    }

    public String getTextFromNdefRecord(NdefRecord ndefRecord)
    {
        String tagContent = null;

        try
        {
            byte[] payload = ndefRecord.getPayload();
            String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
            int languageSize = payload[0] & 0063;
            tagContent = new String(payload, languageSize + 1,
                    payload.length - languageSize - 1, textEncoding);
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return tagContent;
    }

    public void disableForegroundDispatchSystem()
    {
        nfcAdapter.disableForegroundDispatch(activity);
    }

    public void enableForegroundDispatchSystem()
    {
        Intent intent = new Intent(context,activity.getClass()).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        IntentFilter[] intentFilters = new IntentFilter[]{};
        try
        {
            nfcAdapter.enableForegroundDispatch(activity, pendingIntent, intentFilters, null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public NfcAdapter getNfcAdapter()
    {
        return nfcAdapter;
    }

    public String readTextFromTag(Intent intent)
    {
        String result = "";

        Parcelable[] parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

        if(parcelables != null && parcelables.length >0)
        {
            result = readTextFromMessage((NdefMessage) parcelables[0]);
        }
        return result;
    }
}