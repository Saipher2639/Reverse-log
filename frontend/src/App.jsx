import Sidebar from "./components/Sidebar";
import Topbar from "./components/Topbar";
import Dashboard from "./pages/Dashboard";

function App() {
  return (
      <div className="flex min-h-screen bg-gray-50">

        <Sidebar />

        <div className="flex-1">

          <Topbar />

          <Dashboard />

        </div>

      </div>
  );
}

export default App;