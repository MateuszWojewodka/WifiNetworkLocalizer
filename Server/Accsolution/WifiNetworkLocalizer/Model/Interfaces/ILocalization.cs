using Model.Database_Classes;
using Model.Entity_Classes;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WifiNetworkLocalizer.Model.Message_Types;

namespace WifiNetworkLocalizer.Model.Database_Handlers
{
    public interface ILocalization
    {
        Point GetNearestXYLocalizationPoint(int id, FourRSSISignals threeMacIds);

        DeterminantMacIds GetFourDeterminantMacIds(string roomName);

        List<RoomInfo> GetPossibleRooms();

        void SetFourMeasurmentMacIds(DeterminantMacIds threeMacIds);

        void AddRSSIMeasurmentInXYPoint(RSSIMeasurmentPoint RSSIMeasurmentPoint);
    }
}
