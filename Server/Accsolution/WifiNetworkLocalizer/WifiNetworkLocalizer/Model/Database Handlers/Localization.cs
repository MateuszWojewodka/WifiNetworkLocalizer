using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WifiNetworkLocalizer.Model.Message_Types;

namespace WifiNetworkLocalizer.Model.Database_Handlers
{
    public interface Localization
    {
        Point GetLocalizationPoint(ThreeMacIds threeMacIds);

        ThreeMacIds GetThreeMeasurmentMacIds(string buildingName);

        List<String> GetPossibleBuildings();
    }
}
