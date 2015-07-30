# HandyAndroid
Simple, Handy Android Helper.

-
## Initialize the Views of Activity
    import kr.unifox.handy.Handy;
    ...
    Handy h = new Handy(this);
    
    // Get the View without casting
    TextView tv = h.get(R.id.tv_hello);
    EditText et = h.get(R.id.et_name);
    
    // Set the text "hello" to TextView hello
    h._tv(R.id.hello, "Hello Friends");
    h._tvf(R.id.hello, "Hello %s", "Johnathan");
  
### Using HandyActivity
    class MainActivity extends HandyActivity {
      @Override void onCreate(Bundle savedInstanceBundle)
      {
        ...
        // Using handy without create instance
        TextView tv = get(R.id.tv_hello);
        EditText et = get(R.id.et_name);
        
        _tv(R.id.hello, "Hello Friends");
        _tvf(R.id.hello, "Hello %s", "Johnathan");
      }
    }
  
### Using initializeResouces with IDResource

If you call initializeResources() method, it is initialized to fields that has IDResource annotation
toast(), onClick() is simple utility method

    import kr.unifox.handy.IDResource;
    import kr.unifox.handy.HandyActivity;
    public class MainActivity extends HandyActivity 
    {
      @IDResource(R.id.tv_a)
      TextView tvA;
      @IDResource(R.id.tv_b)
      TextView tvB;
  
      @IDResource(R.id.b)
      Button b;
      
      @Override
      protected void onCreate(Bundle savedInstanceState) 
      {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);
          
          try {
              // Initialize fields automatically
              initializeResources();
  
              // set OnClickListener for resources
              onClick(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String a = tvA.getText().toString();
                        String b = tvB.getText().toString();
    
                        tvA.setText(b);
                        tvB.setText(a);
                    }
                }, 
                R.id.button_a,
                R.id.button_b,
                ... // Variable Arguments
              );
          }
          catch(Exception e)
          {
              e.printStackTrace();
              // simple toast, true=LENGTH_LONG and false=LENGTH_SHORT
              toast("Exception occured while initialize", true);
          }
      }
-
## Simple Alert,
    
    import kr.unifox.handy.HandyActivity;
    import kr.unifox.handy.Alert2Listener;
    
    class MainActivity extends HandyActivity {
      @Override void onCreate(Bundle savedInstanceBundle)
      {
        // show alert dialog "Hello" with button "Ok"
        alert (
          "Hello",            // title text
          "You run my app",   // message text
          R.id.ok,            // Button text string id
          null                // Run after press button
        );
          
        // show alert dialog with 2 buttons(positive, negative)
        alert2 (
          "Hello",            // title text
          "You run my app",   // message text
          R.id.ok,            // Positive button text string id
          R.id.cancle,        // Negative button text string id
          new Alert2Listener()// Run after press the button(positive or negative)
          {
            @Override public void handle(boolean positive) 
            {
              if(positive)
                do_something()
              else
                do_nothing()
            }
          }
        );
      }
    }
